package de.upb.docgen.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PredicateTreeGenerator {


    public static Map<String, TreeNode<String>> buildDependencyTreeMap(Map<String, Set<String>> mappedClassnamePredicates) {
        Map<String, TreeNode<String>> chainMap = new HashMap<>();
        for (String classname : mappedClassnamePredicates.keySet()) {
            TreeNode<String> root = new TreeNode<>(classname);
            for (String nextInChain : mappedClassnamePredicates.get(classname)) {
                TreeNode<String> child = new TreeNode<>(nextInChain);
                //recursive to populate chain for child
                populatePredicateTree(child, nextInChain, mappedClassnamePredicates);
                root.addChild(child);
            }
            chainMap.put(classname, root);
        }
        return chainMap;
    }

    private static TreeNode<String> populatePredicateTree(TreeNode<String> firstChild, String nextInChain, Map<String, Set<String>> mappedClassNamePredicates) {
        if (mappedClassNamePredicates.get(nextInChain).size() == 0) {
            return firstChild;
        }

        for (String child : mappedClassNamePredicates.get(nextInChain)) {
            if (firstChild.getData().equals(child)) {
                return firstChild;
            }

            for (TreeNode children : firstChild.getChildren()) {
                if (children.getData().equals(child)) {
                    return firstChild;
                }
            }
            firstChild.addChild(child);
            populatePredicateTree(firstChild, child , mappedClassNamePredicates);
            return firstChild;
        }
        return null;

    }
}