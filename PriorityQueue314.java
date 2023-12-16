/*  Student information for assignment:
 *
 *  On OUR honor, Rakesh and Milan, this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 1
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID:mpd2292
 *  email address: mdhaduk7@gmail.com
 *  Grader name: Casey
 *
 *  Student 2
 *  UTEID: rps2439
 *  email address: rakesh.p.singh.college@gmail.com
 *
 */

import java.util.LinkedList;

public class PriorityQueue314<E extends Comparable<E>> {
    // internal storage for PriorityQueue314
    private final LinkedList<E> STORAGE;

    /**
     * Initialize the inner storage container
     * pre: none
     * post: initialized STORAGE
     */
    public PriorityQueue314() {
        STORAGE = new LinkedList<>();
    }

    /**
     * Adds newNode to STORAGE while maintaining the natural ordering of the list
     * pre: newNode != null
     * post: newNode is not changed. If newNode has the highest natural ordering out
     * of the
     * current list, it is added to the end of the list, and if element(s) with same
     * prioritization already exist, it is added right before the element with the
     * next
     * highest priority
     *
     * @param newNode is a non null element
     * @return true if element is added. Always return true.
     */
    public boolean enqueue(E newNode) {
        // check precondition
        if (newNode == null) {
            throw new IllegalArgumentException("the entered element" +
                    "cannot be null");
        }

        // add newNode to the end of the list, if the list is empty or if
        // the priority of newNode is greater than all other elements in the List
        if (STORAGE.isEmpty() || newNode.compareTo(STORAGE.getLast()) >= 0) {
            STORAGE.add(newNode);
            return true;
        }

        // otherwise, add newNode right before the element with the next highest
        // priority in the list
        for (int i = 0; i < STORAGE.size(); i++) {
            if (STORAGE.get(i).compareTo(newNode) > 0) {
                STORAGE.add(i, newNode);
                return true;
            }
        }
        return true;
    }

    /**
     * Removes and gets the first element in the List
     *
     * @return the first element of the List
     */
    public E dequeue() {
        return STORAGE.removeFirst();
    }

    /**
     * Gets the size of this List
     *
     * @return the size of this List
     */
    public int size() {
        return STORAGE.size();
    }

    /**
     * Gets a string representation of this List
     *
     * @return a string representation of this List
     */
    public String toString() {
        return STORAGE.toString();
    }

    /**
     * Gets the internal storage container of this List
     *
     * @return the LinkedList which acts as the internal storage
     *         container of this list
     */
    public LinkedList<E> getStorage() {
        return STORAGE;
    }

}
