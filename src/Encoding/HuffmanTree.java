package Encoding;

import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;

public class HuffmanTree {

    public static class HuffNode implements Comparable<HuffNode> {

        // fields
        public int value;
        public int weight;
        public HuffNode leftTree;
        public HuffNode rightTree;
        public HuffNode parent;

        // constructors
        public HuffNode() {
            parent = null;
        }

        public HuffNode(int v, int w, HuffNode lTree, HuffNode rTree, HuffNode par) {
            value = v;
            weight = w;
            leftTree = lTree;
            rightTree = rTree;
            parent = par;
        }

        // setters/getters
        @Override
        public int compareTo(HuffNode rhs) {
            return weight - rhs.weight;
        }

        @Override
        public String toString() {
            String str = "";
            str += this.value;
            return str;
        }
    }

    // object representing a Huffman tree
    public static class HuffTree {

        // fields
        public int size = 0;
        public HuffNode root = new HuffNode();
        public PriorityQueue<HuffNode> huffQueue = new PriorityQueue();
        public SortedMap<String, Character> SCmap = new TreeMap<>();
        public SortedMap<Character, String> CSmap = new TreeMap<>();

        // constructor
        public HuffTree(int[] arr) {
            // get the counts
            this.size = arr.length;

            // build huffQueue from frequencies given
            for (int i = 0; i < this.size; i++) {
                huffQueue.offer(new HuffNode((char) i, arr[i], null, null, null));
            }

            // build Huffman tree from queue
            createTree();

            // build code table from Huffman tree
            createTable(this.root, "");
        }

        // setters/getters
        /**
         * creates Huffman Tree from frequencies and values
         *
         * @param null
         */
        private void createTree() {
            // while elements remain in huffQueue, add to tree
            while (huffQueue.size() > 1) {
                // pop off two minimum elements in huffQueue
                HuffNode tempL = huffQueue.poll();
                HuffNode tempR = huffQueue.poll();

                // create root for two minimum elements and build tree
                HuffNode parent = new HuffNode(0, tempL.weight + tempR.weight, tempL, tempR, null);
                tempL.parent = parent;
                tempR.parent = parent;

                // add new tree back in huffQueue
                huffQueue.offer(parent);
                this.size++;
            }

            // set HuffTree root to remaining element in huffQueue
            this.root = huffQueue.peek();
        }

        /**
         * creates code table for a Huffman tree
         *
         * @param HuffNode -- root for tree, string -- for building paths
         */
        private void createTable(HuffNode curr, String str) {
            // if iterator is null, return
            if (curr == null) {
                return;
            }

            // else if leaf, display path and value
            if (curr.leftTree == null && curr.rightTree == null) {
                char tempChar;
                if (curr.value == 32) {
                    tempChar = ' ';
                }

                if (curr.value == 10) {
                    tempChar = 'n';
                } else {
                    tempChar = (char) curr.value;
                }
                // add value and path to sorted maps

                CSmap.put(tempChar, str);
                SCmap.put(str, tempChar);

            }

            // add 0 if before moving to left child
            str += "0";
            // recursively call in pre-order
            createTable(curr.leftTree, str);

            // adjust path and add 1 before moving to right child
            str = str.substring(0, str.length() - 1);
            str += "1";
            createTable(curr.rightTree, str);
        }

        /**
         * display given Huffman tree using pre-order traversal
         *
         * @param HuffNode -- root of tree to be displayed
         */
        // global variable used for representing 'levels' of tree
        String tacks = "";

        public void getTree(HuffNode curr) {
            // if iterator is null, return
            if (curr == null) {
                return;
            }

            // else if leaf, display level, weight, and value
            if (curr.leftTree == null && curr.rightTree == null) {
                // case statements to handle displaying space and newline
                switch (curr.value) {
                    case 32:
                        System.out.println(tacks + curr.weight + ": sp");
                        break;
                    case 10:
                        System.out.println(tacks + curr.weight + ": nl");
                        break;
                    default:
                        System.out.println(tacks + curr.weight + ": " + (char) curr.value);
                        break;
                }
            } // else display level and weight
            else {
                System.out.println(tacks + curr.weight);
            }

            // increment level marker
            tacks += "- ";
            // recursively call in pre-order
            getTree(curr.leftTree);
            getTree(curr.rightTree);
            // decrement level marker
            tacks = tacks.substring(0, tacks.length() - 2);
        }

        /**
         * returns size of a given Huffman tree
         *
         * @return int -- size of tree
         */
        public int getSize() {
            return this.size;
        }

        /**
         * returns encoded bits for a given string
         *
         * @param input
         * @return String -- encoded version of original string
         */
        public String encode(String input) {
            // create empty string to hold code
            String str = "";
            // iterate through given string
            for (int x = 0; x < input.length(); x++) {
                str += CSmap.get(input.charAt(x));
            }
            return str;
        }

        /**
         * returns decoded string for a given set of bits
         *
         * @param bits
         * @return String -- decoded version of bits
         */
        public String decode(String bits) {
            // create empty string to hold decoded message
            String decodedStr = "";

            // iterate through bits
            for (int i = 0; i < bits.length(); i++) {
//                if(SCmap.get(bits.substring(0, i + 1)) == null)
//                    return null;
                if (SCmap.get(bits.substring(0, i + 1)) != null) {
                    decodedStr += SCmap.get(bits.substring(0, i + 1));
                    bits = bits.substring(i + 1);
                    i = 0;
                }
            }
            return decodedStr;
        }
    }
}
