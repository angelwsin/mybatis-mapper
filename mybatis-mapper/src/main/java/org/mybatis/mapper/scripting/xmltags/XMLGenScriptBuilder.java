package org.mybatis.mapper.scripting.xmltags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;
import org.apache.ibatis.session.Configuration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLGenScriptBuilder extends BaseBuilder {

	  private final XNode context;
	  private boolean isDynamic = true;
	  private final Class<?> parameterType;

	  public XMLGenScriptBuilder(Configuration configuration, XNode context) {
	    this(configuration, context, null);
	  }

	  public XMLGenScriptBuilder(Configuration configuration, XNode context, Class<?> parameterType) {
	    super(configuration);
	    this.context = context;
	    this.parameterType = parameterType;
	  }

	  public SqlSource parseScriptNode() {
	    List<SqlNode> contents = parseDynamicTags(context);
	    MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
	    SqlSource sqlSource = null;
	    if (isDynamic) {
	      sqlSource = new DynamicGenSqlSource(configuration, rootSqlNode);
	    } else {
	      sqlSource = new RawSqlSource(configuration, rootSqlNode, parameterType);
	    }
	    return sqlSource;
	  }
	  
	  

	  List<SqlNode> parseDynamicTags(XNode node) {
	    List<SqlNode> contents = new ArrayList<SqlNode>();
	    NodeList children = node.getNode().getChildNodes();
	    for (int i = 0; i < children.getLength(); i++) {
	      XNode child = node.newXNode(children.item(i));
	      if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE) {
	        String data = child.getStringBody("");
	        TextGenSqlNode textSqlNode = new TextGenSqlNode(data);
	        contents.add(textSqlNode);
	      } else if (child.getNode().getNodeType() == Node.ELEMENT_NODE) { // issue #628
	        String nodeName = child.getNode().getNodeName();
	        NodeHandler handler = nodeHandlers(nodeName);
	        if (handler == null) {
	          throw new BuilderException("Unknown element <" + nodeName + "> in SQL statement.");
	        }
	        handler.handleNode(child, contents);
	        //isDynamic = true;
	      }
	    }
	    return contents;
	  }
	  
	  List<SqlNode> parseDynamicAttrs(XNode node) {
		  List<SqlNode> contents = new ArrayList<SqlNode>();
		  String where = context.getStringAttribute("where");
		  String colums = context.getStringAttribute("colums");
		  String append = context.getStringAttribute("append");
		  String noif = context.getStringAttribute("noif");
		  if(Objects.nonNull(where)&&!where.equals("")){
			  contents.add(new WhereGenSqlNode(where));
		  }else{
			  contents.add(new WhereGenSqlNode("all"));
		  }
		  if(Objects.nonNull(colums)&&!colums.equals("")){
			  contents.add(new ColumsGenSqlNode(colums));
		  }else{
			  contents.add(new ColumsGenSqlNode("all"));
		  }
		  if(Objects.nonNull(append)&&!append.equals("")){
			  contents.add(new TextGenSqlNode(append));
		  }
		  if(Objects.nonNull(noif)&&!noif.equals("")){
			  contents.add(new NoIfGenSqlNode(noif));
		  }
		   return contents;
		  }

	  NodeHandler nodeHandlers(String nodeName) {
	    Map<String, NodeHandler> map = new HashMap<String, NodeHandler>();
	    map.put("trim", new TrimHandler());
	    map.put("where", new WhereHandler());
	    map.put("colums", new ColumnsHandler());
	    map.put("set", new SetHandler());
	    map.put("foreach", new ForEachHandler());
	    map.put("if", new IfHandler());
	    map.put("noif", new NoIfHandler());
	    map.put("choose", new ChooseHandler());
	    map.put("when", new IfHandler());
	    map.put("otherwise", new OtherwiseHandler());
	    map.put("bind", new BindHandler());
	    return map.get(nodeName);
	  }

	  private interface NodeHandler {
	    void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);
	  }

	  private class BindHandler implements NodeHandler {
	    public BindHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      final String name = nodeToHandle.getStringAttribute("name");
	      final String expression = nodeToHandle.getStringAttribute("value");
	      final VarDeclSqlNode node = new VarDeclSqlNode(name, expression);
	      targetContents.add(node);
	    }
	  }

	  private class TrimHandler implements NodeHandler {
	    public TrimHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> contents = parseDynamicTags(nodeToHandle);
	      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
	      String prefix = nodeToHandle.getStringAttribute("prefix");
	      String prefixOverrides = nodeToHandle.getStringAttribute("prefixOverrides");
	      String suffix = nodeToHandle.getStringAttribute("suffix");
	      String suffixOverrides = nodeToHandle.getStringAttribute("suffixOverrides");
	      TrimSqlNode trim = new TrimSqlNode(configuration, mixedSqlNode, prefix, prefixOverrides, suffix, suffixOverrides);
	      targetContents.add(trim);
	    }
	  }
	  private class ColumnsHandler implements NodeHandler {
		    public ColumnsHandler() {
		      // Prevent Synthetic Access
		    }

		    @Override
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		    	String colums = nodeToHandle.getStringAttribute("cols");
		        targetContents.add(new ColumsGenSqlNode(colums));
		    }
		  }

	  private class WhereHandler implements NodeHandler {
	    public WhereHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	    	 String where = nodeToHandle.getStringAttribute("cols");
	    	 WhereGenSqlNode wherenode = new WhereGenSqlNode(where);
	      targetContents.add(wherenode);
	    }
	  }

	  private class SetHandler implements NodeHandler {
	    public SetHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> contents = parseDynamicTags(nodeToHandle);
	      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
	      SetSqlNode set = new SetSqlNode(configuration, mixedSqlNode);
	      targetContents.add(set);
	    }
	  }

	  private class ForEachHandler implements NodeHandler {
	    public ForEachHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> contents = parseDynamicTags(nodeToHandle);
	      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
	      String collection = nodeToHandle.getStringAttribute("collection");
	      String item = nodeToHandle.getStringAttribute("item");
	      String index = nodeToHandle.getStringAttribute("index");
	      String open = nodeToHandle.getStringAttribute("open");
	      String close = nodeToHandle.getStringAttribute("close");
	      String separator = nodeToHandle.getStringAttribute("separator");
	      ForEachSqlNode forEachSqlNode = new ForEachSqlNode(configuration, mixedSqlNode, collection, index, item, open, close, separator);
	      targetContents.add(forEachSqlNode);
	    }
	  }

	  private class IfHandler implements NodeHandler {
	    public IfHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> contents = parseDynamicTags(nodeToHandle);
	      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
	      String test = nodeToHandle.getStringAttribute("test");
	      IfSqlNode ifSqlNode = new IfSqlNode(mixedSqlNode, test);
	      targetContents.add(ifSqlNode);
	    }
	  }
	  
	  private class NoIfHandler implements NodeHandler {
		    public NoIfHandler() {
		      // Prevent Synthetic Access
		    }

		    @Override
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		    	String noif = nodeToHandle.getStringAttribute("cols");
		        targetContents.add(new NoIfGenSqlNode(noif));
		    }
		  }

	  private class OtherwiseHandler implements NodeHandler {
	    public OtherwiseHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> contents = parseDynamicTags(nodeToHandle);
	      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
	      targetContents.add(mixedSqlNode);
	    }
	  }

	  private class ChooseHandler implements NodeHandler {
	    public ChooseHandler() {
	      // Prevent Synthetic Access
	    }

	    @Override
	    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
	      List<SqlNode> whenSqlNodes = new ArrayList<SqlNode>();
	      List<SqlNode> otherwiseSqlNodes = new ArrayList<SqlNode>();
	      handleWhenOtherwiseNodes(nodeToHandle, whenSqlNodes, otherwiseSqlNodes);
	      SqlNode defaultSqlNode = getDefaultSqlNode(otherwiseSqlNodes);
	      ChooseSqlNode chooseSqlNode = new ChooseSqlNode(whenSqlNodes, defaultSqlNode);
	      targetContents.add(chooseSqlNode);
	    }

	    private void handleWhenOtherwiseNodes(XNode chooseSqlNode, List<SqlNode> ifSqlNodes, List<SqlNode> defaultSqlNodes) {
	      List<XNode> children = chooseSqlNode.getChildren();
	      for (XNode child : children) {
	        String nodeName = child.getNode().getNodeName();
	        NodeHandler handler = nodeHandlers(nodeName);
	        if (handler instanceof IfHandler) {
	          handler.handleNode(child, ifSqlNodes);
	        } else if (handler instanceof OtherwiseHandler) {
	          handler.handleNode(child, defaultSqlNodes);
	        }
	      }
	    }

	    private SqlNode getDefaultSqlNode(List<SqlNode> defaultSqlNodes) {
	      SqlNode defaultSqlNode = null;
	      if (defaultSqlNodes.size() == 1) {
	        defaultSqlNode = defaultSqlNodes.get(0);
	      } else if (defaultSqlNodes.size() > 1) {
	        throw new BuilderException("Too many default (otherwise) elements in choose statement.");
	      }
	      return defaultSqlNode;
	    }
	  }

	}

