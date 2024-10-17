package oms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static TreeNode jsonToTree(JsonNode jsonNode){
        if (jsonNode == null || !jsonNode.has("value")) {
            return null;
        }
        TreeNode node = new TreeNode(jsonNode.get("value").asInt());

        if (jsonNode.has("left")) {
            node.left = jsonToTree(jsonNode.get("left"));
        }

        if (jsonNode.has("right")) {
            node.right = jsonToTree(jsonNode.get("right"));
        }

        return node;
    }

    public static List<Integer> findMaxValuePath(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        findPath(root, new ArrayList<>(), result);
        return result;
    }

    private static void findPath(TreeNode node, List<Integer> currentPath, List<Integer> maxValuePath) {
        if (node == null) return;

        currentPath.add(node.value);

        if (node.left == null && node.right == null) {
            if (maxValuePath.isEmpty() || node.value > maxValuePath.get(maxValuePath.size() - 1)) {
                maxValuePath.clear();
                maxValuePath.addAll(currentPath);
            }
        }
        findPath(node.left, currentPath, maxValuePath);
        findPath(node.right, currentPath, maxValuePath);

        currentPath.remove(currentPath.size() - 1);
    }



    public static void main(String[] args) throws JsonProcessingException {
        String jsonString = """
        {
            "value": 5,
            "left": {
                "value": 8,
                "left": {
                    "value": 2
                }
            },
            "right": {
                "value": 3,
                "left": {
                    "value": 9
                },
                "right": {
                    "value": 4
                }
            }
        }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootJson = mapper.readTree(jsonString);
        TreeNode root = jsonToTree(rootJson);

        List<Integer> maxPath = findMaxValuePath(root);

        System.out.println(maxPath);

    }
}