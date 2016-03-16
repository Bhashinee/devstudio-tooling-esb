/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.datamapper.diagram.tree.generator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.json.simple.JSONObject;
import org.wso2.developerstudio.datamapper.DataMapperFactory;
import org.wso2.developerstudio.datamapper.Element;
import org.wso2.developerstudio.datamapper.TreeNode;
import org.wso2.developerstudio.datamapper.diagram.Activator;
import org.wso2.developerstudio.datamapper.diagram.tree.model.Tree;
import org.wso2.developerstudio.datamapper.impl.TreeNodeImpl;
import org.wso2.developerstudio.eclipse.logging.core.IDeveloperStudioLog;
import org.wso2.developerstudio.eclipse.logging.core.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SchemaTransformer implements ISchemaTransformer {

	private static final String JSON_SCHEMA_TITLE = "title";
	private static final String JSON_SCHEMA_ITEMS = "items";
	private static final String JSON_SCHEMA_ARRAY = "array";
	private static final String JSON_SCHEMA_REQUIRED = "required";
	private static final String JSON_SCHEMA_SCHEMA_VALUE = "$schema";
	private static final String JSON_SCHEMA_ID = "id";
	private static final String JSON_SCHEMA_PROPERTIES = "properties";
	private static final String JSON_SCHEMA_TYPE = "type";
	private static final String JSON_SCHEMA_OBJECT = "object";
	private static final String JSON_SCHEMA_ARRAY_ITEMS_ID = "items_id";
	private static final String JSON_SCHEMA_ARRAY_ITEMS_TYPE = "items_type";
	private static final String JSON_SCHEMA_ARRAY_ITEMS_REQUIRED = "items_required";

	private static final String PREFIX = "@";

	private static IDeveloperStudioLog log = Logger.getLog(Activator.PLUGIN_ID);
	private static String ERROR_TEXT = "File cannot be found ";
	private static String ERROR_WRITING_SCHEMA_FILE = "Error writing to schema file";

	Map<String, Object> jsonSchemaMap;

	@SuppressWarnings("unchecked")
	@Override
	public TreeNode generateTree(String content, TreeNode inputRootTreeNode) throws NullPointerException,
			IllegalArgumentException, IOException {
		InputStream schema = new ByteArrayInputStream(content.getBytes());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonSchemaMap = objectMapper.readValue(schema, Map.class);
		} catch (JsonParseException e) {
			log.error("error in parsing the JSONSchema", e);
		} catch (JsonMappingException e) {
			log.error("error in mapping the JSONSchema", e);
		} catch (IOException e) {
			log.error("error in processing the JSONSchema", e);
		}
		// Creates the root element
		int count = 1;
		inputRootTreeNode = createTreeNode(inputRootTreeNode, count, getName(jsonSchemaMap), jsonSchemaMap,
				getSchemaType(jsonSchemaMap));
		// Creates the tree by adding tree node and elements
		inputRootTreeNode = setProperties(jsonSchemaMap, inputRootTreeNode, count);

		return inputRootTreeNode;

	}

	/**
	 * Gets the schema name
	 * 
	 * @param jsonSchemaMap
	 *            schema
	 * @return name
	 */
	public String getName(Map<String, Object> jsonSchemaMap) {
		String schemaName = (String) jsonSchemaMap.get(JSON_SCHEMA_TITLE);
		if (schemaName != null) {
			return schemaName;
		} else {
			log.error("Invalid input schema, schema name not found.");
			throw new NullPointerException("Invalid input schema, schema name not found.");
		}
	}

	/**
	 * Gets the schema type
	 * 
	 * @param schema
	 *            schema
	 * @return type
	 */
	private String getSchemaType(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_TYPE)) {
			Object type = jsonSchemaMap.get(JSON_SCHEMA_TYPE);
			if (type instanceof String) {
				return (String) type;
			} else {
				log.error("Invalid input schema, invalid schema type found");
				throw new IllegalArgumentException("Illegal format " + type.getClass() + " value found under key : "
						+ JSON_SCHEMA_TYPE);
			}
		} else {
			log.error("Invalid input schema, schema type not found.");
			throw new IllegalArgumentException("Given schema does not contain value under key : " + JSON_SCHEMA_TYPE);
		}
	}

	/**
	 * Gets the schema key value
	 * 
	 * @param schema
	 *            map
	 * @return schema value
	 */
	private String getSchemaValue(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_SCHEMA_VALUE)) {
			Object type = jsonSchemaMap.get(JSON_SCHEMA_SCHEMA_VALUE);
			if (type instanceof String) {
				return (String) type;
			} else {
				log.error("Invalid input schema, invalid schema value found");
				throw new IllegalArgumentException("Illegal format " + type.getClass() + " value found under key : "
						+ JSON_SCHEMA_SCHEMA_VALUE);
			}
		}
		return null;
	}

	/**
	 * Gets the schema id value
	 * 
	 * @param schema
	 *            map
	 * @return id value
	 */
	private String getIDValue(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_ID)) {
			Object type = jsonSchemaMap.get(JSON_SCHEMA_ID);
			if (type instanceof String) {
				return (String) type;
			} else {
				log.error("Invalid input schema, invalid ID value found");
				throw new IllegalArgumentException("Illegal format " + type.getClass() + " value found under key : "
						+ JSON_SCHEMA_ID);
			}
		}
		return null;
	}

	/**
	 * Gets the required value
	 * 
	 * @param jsonSchemaMap
	 *            schema
	 * @return value
	 */
	private String getRequiredValue(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_REQUIRED)) {
			Object type = jsonSchemaMap.get(JSON_SCHEMA_REQUIRED);
			if (type instanceof ArrayList) {
				@SuppressWarnings("unchecked")
				String value = String.join(",", (ArrayList<String>) type);
				return value;
			} else {
				log.error("Invalid input schema, invalid required value found");
				throw new IllegalArgumentException("Illegal format " + type.getClass() + " value found under key : "
						+ JSON_SCHEMA_REQUIRED);
			}
		}
		return null;
	}

	/**
	 * Gets the schema properties
	 * 
	 * @param schema
	 *            schema
	 * @return property map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSchemaProperties(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_PROPERTIES)) {
			return (Map<String, Object>) jsonSchemaMap.get(JSON_SCHEMA_PROPERTIES);
		} else {
			log.error("Invalid input schema, property value not found");
			throw new IllegalArgumentException("Given schema does not contain value under key : "
					+ JSON_SCHEMA_PROPERTIES);
		}
	}

	/**
	 * Gets schema items
	 * 
	 * @param schema
	 *            schema
	 * @return item map
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSchemaItems(Map<String, Object> jsonSchemaMap) {
		if (jsonSchemaMap.containsKey(JSON_SCHEMA_ITEMS)) {
			return (Map<String, Object>) jsonSchemaMap.get(JSON_SCHEMA_ITEMS);
		} else {
			log.error("Invalid input schema, items value not found");
			throw new IllegalArgumentException("Given schema does not contain value under key : " + JSON_SCHEMA_ITEMS);
		}
	}

	/**
	 * Sets the properties
	 * 
	 * @param jsonSchemaMap
	 *            schema
	 * @param inputRootTreeNode
	 *            treenode
	 * @param count
	 *            leve;
	 * @return tree node
	 */
	private TreeNode setProperties(Map<String, Object> jsonSchemaMap, TreeNode inputRootTreeNode, int count) {
		// Gets the schema properties
		Map<String, Object> propertyMap = getSchemaProperties(jsonSchemaMap);
		Map<String, Object> attributeMap = new LinkedHashMap<String, Object>();
		Map<String, Object> sortedMap = getAttributeMap(propertyMap, attributeMap);
		//If there is an attribute, add the rest of the elements to the map after the attribute
		if (sortedMap.size() > 0) {
			sortedMap.putAll(propertyMap);
		} else {
			sortedMap = propertyMap;
		}
		Set<String> elementKeys = sortedMap.keySet();

		TreeNode treeNode = null;
		org.wso2.developerstudio.datamapper.Element element;
		count++;
		for (String elementKey : elementKeys) {
			@SuppressWarnings("unchecked")
			Map<String, Object> subSchema = (Map<String, Object>) sortedMap.get(elementKey);
			// Gets the schema type of the sub schema
			String schemaType = getSchemaType(subSchema);
			if (JSON_SCHEMA_OBJECT.equals(schemaType)) {
				// Creates the tree node
				treeNode = createTreeNode(null, count, elementKey, subSchema, schemaType);
				inputRootTreeNode.getNode().add(treeNode);
				setProperties(subSchema, treeNode, count);
			} else if (JSON_SCHEMA_ARRAY.equals(schemaType)) {
				treeNode = createTreeNode(null, count, elementKey, subSchema, schemaType);
				inputRootTreeNode.getNode().add(treeNode);
				setProperties(getSchemaItems(subSchema), treeNode, count);
			} else {
				//When there is an attribute, add it as an element to the tree
				if (elementKey.startsWith(PREFIX)) {
					element = createElement(count, elementKey, subSchema, schemaType);
					inputRootTreeNode.getElement().add(element);
				} else {
					treeNode = createTreeNode(null, count, elementKey, subSchema, schemaType);
					inputRootTreeNode.getNode().add(treeNode);
					//If an element contained properties eg: attribute 
					if(subSchema.get(JSON_SCHEMA_PROPERTIES) != null){
					 setProperties(subSchema, treeNode, count);
					}
				}

			}
		}
		return inputRootTreeNode;

	}

	/**
	 * Gets the attribute mape
	 * 
	 * @param propertyMap
	 *            property map
	 * @param sortedMap
	 *            attribute map
	 * @return attribute map
	 */
	private Map<String, Object> getAttributeMap(Map<String, Object> propertyMap, Map<String, Object> sortedMap) {
		for (Iterator<Map.Entry<String, Object>> it = propertyMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = it.next();
			if (entry.getKey().startsWith(PREFIX)) {
				//If there is an attribute, remove it from the property map and add it to the sorted map
				it.remove();
				sortedMap.put(entry.getKey(), entry.getValue());
			}
		}
		return sortedMap;
	}

	/**
	 * Creates and element
	 * 
	 * @param inputRootTreeNode
	 *            root tree node
	 * @param count
	 *            level
	 * @param elementKey
	 *            element key
	 * @param subSchema
	 *            sub schema
	 * @param schemaType
	 *            schema type
	 * @return element
	 */
	private Element createElement(int count, String elementKey, Map<String, Object> subSchema, String schemaType) {
		org.wso2.developerstudio.datamapper.Element element;
		element = DataMapperFactory.eINSTANCE.createElement();
		element.setName(elementKey);
		element.setLevel(count);
		element.getProperties().put(JSON_SCHEMA_TYPE, schemaType);
		// Sets the id value if available
		if (getIDValue(subSchema) != null) {
			element.getProperties().put(JSON_SCHEMA_ID, getIDValue(subSchema));
		}
		return element;
	}

	/**
	 * Creates the inner tree node
	 * 
	 * @param inputRootTreeNode
	 * @param inputRootTreeNode
	 *            root tree node
	 * @param count
	 *            level
	 * @param elementKey
	 *            element
	 * @param subSchema
	 *            sub schema
	 * @param schemaType
	 *            schema type
	 * @return tree node
	 */
	private TreeNode createTreeNode(TreeNode inputRootTreeNode, int count, String elementKey,
			Map<String, Object> subSchema, String schemaType) {
		TreeNode treeNode;
		if (inputRootTreeNode == null) {
			treeNode = DataMapperFactory.eINSTANCE.createTreeNode();
		} else {
			treeNode = inputRootTreeNode;
		}
		treeNode.setName(elementKey);
		treeNode.setLevel(count);
		treeNode.getProperties().put(JSON_SCHEMA_TYPE, schemaType);
		// Sets the schema key if available
		if (getSchemaValue(subSchema) != null) {
			treeNode.getProperties().put(JSON_SCHEMA_SCHEMA_VALUE, getSchemaValue(subSchema));
		}
		// Sets the id value if available
		if (getIDValue(subSchema) != null) {
			treeNode.getProperties().put(JSON_SCHEMA_ID, getIDValue(subSchema));
		}
		// Sets the required value
		if (getRequiredValue(subSchema) != null) {
			treeNode.getProperties().put(JSON_SCHEMA_REQUIRED, getRequiredValue(subSchema));
		}

		if (schemaType.equals(JSON_SCHEMA_ARRAY)) {
			if (subSchema.get(JSON_SCHEMA_ITEMS) != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> itemsSchema = (Map<String, Object>) subSchema.get(JSON_SCHEMA_ITEMS);
				treeNode.getProperties().put(JSON_SCHEMA_ARRAY_ITEMS_ID, itemsSchema.get(JSON_SCHEMA_ID).toString());
				treeNode.getProperties()
						.put(JSON_SCHEMA_ARRAY_ITEMS_TYPE, itemsSchema.get(JSON_SCHEMA_TYPE).toString());
				treeNode.getProperties().put(JSON_SCHEMA_ARRAY_ITEMS_REQUIRED, getRequiredValue(itemsSchema));
			}
		}
		return treeNode;
	}

	@Override
	public String getSchemaContentFromFile(String path) {
		File jschema = new File(path);
		String entireFileText = null;
		try {
			entireFileText = new Scanner(jschema).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			log.error(ERROR_TEXT, e);
		}
		return entireFileText;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSchemaContentFromModel(TreeNodeImpl treeNodeModel, File writeToFile) {

		JSONObject root = new JSONObject();
		JSONObject propertiesObject = new JSONObject();
		if (StringUtils.isNotEmpty(treeNodeModel.getName()) && treeNodeModel.getProperties() != null) {
			insetIFTypeForJsonObject(treeNodeModel, root);
			root.put(JSON_SCHEMA_SCHEMA_VALUE, treeNodeModel.getProperties().get(JSON_SCHEMA_SCHEMA_VALUE));
			root.put(JSON_SCHEMA_TITLE, treeNodeModel.getName());
			root.put(JSON_SCHEMA_PROPERTIES, propertiesObject);
			insertRequiredArray(root, treeNodeModel);
			recursiveTreeGenerator(treeNodeModel, propertiesObject);
		}
		return root.toString();
	}

	@SuppressWarnings("unchecked")
	private void recursiveTreeGenerator(TreeNodeImpl treeNodeModel, JSONObject parent) {
		if (treeNodeModel != null) {
			EList<Element> elemList = treeNodeModel.getElement();
			EList<TreeNode> nodeList = treeNodeModel.getNode();
			for (TreeNode node : nodeList) {
				if (node.getProperties().get(JSON_SCHEMA_TYPE) != null
						&& node.getProperties().get(JSON_SCHEMA_TYPE).equals(JSON_SCHEMA_OBJECT)) {

					JSONObject nodeObject = new JSONObject();
					JSONObject propertiesObject = new JSONObject();
					insetIFTypeForJsonObject(node, nodeObject);
					nodeObject.put(JSON_SCHEMA_PROPERTIES, propertiesObject);
					parent.put(node.getName(), nodeObject);
					insertRequiredArray(nodeObject, node);
					recursiveTreeGenerator((TreeNodeImpl) node, propertiesObject);

				} else if (node.getProperties().get(JSON_SCHEMA_TYPE) != null
						&& node.getProperties().get(JSON_SCHEMA_TYPE).equals(JSON_SCHEMA_ARRAY)) {
					JSONObject arrayObject = new JSONObject();
					JSONObject itemsObject = new JSONObject();
					JSONObject itemProperties = new JSONObject();
					insetIFTypeForJsonObject(node, arrayObject);
					arrayObject.put(JSON_SCHEMA_ITEMS, itemProperties);
					insetIFTypeForJsonObject(node, itemsObject);
					itemProperties.put(JSON_SCHEMA_PROPERTIES, itemsObject);
					parent.put(node.getName(), arrayObject);
					insertRequiredArray(arrayObject, node);
					recursiveTreeGenerator((TreeNodeImpl) node, itemsObject);
				}
			}
			for (Element elem : elemList) {
				if (elem.getProperties().get(JSON_SCHEMA_TYPE) != null) {
					JSONObject elemObject = new JSONObject();
					elemObject.put(JSON_SCHEMA_ID, elem.getProperties().get(JSON_SCHEMA_ID).replace("\\", ""));
					elemObject.put(JSON_SCHEMA_TYPE, elem.getProperties().get(JSON_SCHEMA_TYPE));
					parent.put(elem.getName(), elemObject);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void insetIFTypeForJsonObject(TreeNode node, JSONObject nodeObject) {
		nodeObject.put(JSON_SCHEMA_ID, node.getProperties().get(JSON_SCHEMA_ID).replace("\\", ""));
		nodeObject.put(JSON_SCHEMA_TYPE, node.getProperties().get(JSON_SCHEMA_TYPE));
	}

	@SuppressWarnings("unchecked")
	private void insertRequiredArray(JSONObject parent, TreeNode node) {
		if (node.getProperties().get(JSON_SCHEMA_REQUIRED) != null) {
			parent.put(JSON_SCHEMA_REQUIRED, node.getProperties().get(JSON_SCHEMA_REQUIRED));
		}
	}

	@Override
	public void updateSchemaFile(String content, File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object json = mapper.readValue(content, Object.class);
			FileUtils.writeStringToFile(file, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
		} catch (IOException e) {
			log.error(ERROR_WRITING_SCHEMA_FILE + file.getName(), e);
			return;
		}
	}

	@Override
	public Tree generateTreeFromFile(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}