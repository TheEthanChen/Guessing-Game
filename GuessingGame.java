/*  Name: Ethan Chen
 *  PennKey: etc
 *  Recitation: 217
 *
 *  Execution: java GuessingGame
 * 
 * Attempt to guess user item
 */

public class GuessingGame {
    
    public static String[] questions;
    public static String[][] answers;
    
    /* Description: Populate questions and answers with the data
     * in the file named filename 
     * Input:  The file name as a String
     * Output: void
     */
    public static void readData(String filename) {
        //Read the data
        In inStream = new In(filename);
        int numAnswers = Integer.parseInt(inStream.readLine());
        int numQuestions = Integer.parseInt(inStream.readLine());
        
        //Set up the arrays
        questions = new String[numQuestions];
        answers = new String[numAnswers][numQuestions + 1];
        
        //Fill in the arrays
        for (int i = 0; i < numQuestions; i++) {
            questions[i] = inStream.readLine();
        }
        for (int i = 0; i < numAnswers; i++) {
            answers[i][0] = inStream.readString();
            for (int k = 1; k <= numQuestions; k++) {
                answers[i][k] = inStream.readString();
            }
        }
    }
    
    
    /* Description: print out the number of animals and the number of questions
     * in the data set, the questions' text, and the data set
     * Input:  2D String array of the answers and String array of the questions
     * Output: void
     */
    public static void print(String[][] answers, String[] questions) {
        
        //Print out Number of Answers/Questions
        System.out.println(answers[0].length);
        System.out.println(questions.length);
        
        //Print out Questions and Answers
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
        }
        for (int i = 0; i < answers.length; i++) {
            System.out.print(answers[i][0] + " ");
            for (int k = 1; k <= questions.length; k++) {
                System.out.print(answers[i][k] + " ");
            }
            System.out.println();
        }
    }
    
     /* Description: sort the entire answers data set
      * Input: void
      * Output: void
      */
    public static void sort() {
        sort(0, answers.length, 0);
    }
    
     /* Description:sort answers within the range [top, bottom) using
      * starting with the question 
      * Input: integer value of start of index and integer value of end of
      * index to sort and the integer value of the question to start sorting
      * through
      * Output: void
      */
    public static void sort(int startingAnimalIdx, int endingAnimalIdx,
                            int startingQuestionIdx) {
        //Don't run if parameters incorrect
        if (startingAnimalIdx < 0 || startingQuestionIdx < 0 || 
            startingQuestionIdx >= questions.length) {
            throw new RuntimeException("Please enter the appropriate" + 
                                           "parameters");
        }
        
        //Continue sorting only if last index larger than start index
        if (endingAnimalIdx > startingAnimalIdx + 1) {
            sortRange(startingAnimalIdx, endingAnimalIdx, startingQuestionIdx);
            
            //find the middle of the index
            int split = 
                findSplit(startingAnimalIdx, endingAnimalIdx, 
                       startingQuestionIdx);
        
            //only continue to sort if middle continues to be relevant
            if (startingQuestionIdx < questions.length - 1) {
                if (split != -1) {
                    sort(startingAnimalIdx, split, startingQuestionIdx + 1); 
                    sort(split, endingAnimalIdx, startingQuestionIdx + 1);
                }
                else {
                    sort(startingAnimalIdx, endingAnimalIdx, 
                         startingQuestionIdx + 1);
                }
            }
        }
    }
    
    /* Description: sort the rows in a within in range
     * startingAnimaIdx, endingAnimalIdx)based on the question column 
     * questionIdx
     * Input:  Integer of starting place in index to sort and integer of 
     * ending place in index to sort and the integer column to sort
     * Output: void
     */
    public static void sortRange(int startingAnimalIdx, int endingAnimalIdx,
                                 int questionIdx) {
        //Look to see if previous answer is 'n' compared to current answer 'y'
        for (int i = startingAnimalIdx + 1; i < endingAnimalIdx; i++) {
            for (int j = i; j > startingAnimalIdx; j--) {
                if (answers[j - 1][questionIdx + 1].equals("y") &&
                    answers[j][questionIdx + 1].equals("n")) {
                    //Swap only if prev answer 'y' compared to current 'n'
                    for (int k = 0; k < questions.length + 1; k++) {
                        String swap = answers[j - 1][k];
                        answers[j - 1][k] = answers[j][k];
                        answers[j][k] = swap;
                    }
                }
            }
        }
    }
    
    /* Description: find the first index in answers within the range
     * [top, bottom) where the answer to the question 
     * is "y"
     * Input: integer value of the range of animals to begin and the integer
     * value of where it ends and the question column to compare
     * Output: integer value of the location of the first index in answers
     * where the answer to the question is "y"
     */
    public static int findSplit(int startingAnimalIdx, int endingAnimalIdx,
                                int questionIdx) {
        //stop if only one between start and end
        if (startingAnimalIdx + 1 == endingAnimalIdx) {
            return -1;
        }

        //find midpoint
        int middle = (endingAnimalIdx + startingAnimalIdx) / 2; 
        
        //return midpoint if previous answer 'n' and current 'y'
        if (answers[middle][questionIdx + 1].equals("y")) {
            if (answers[middle - 1][questionIdx + 1].equals("n")) {
                return middle;
            }
            
            //if both 'y' then continue to search only in top half
            else {
                return findSplit(startingAnimalIdx, middle, questionIdx);
            }
        }
        
        //if 'n', then continue looking for a 'y' in bottom half
        else {
            return findSplit(middle, endingAnimalIdx, questionIdx);
        }
    }
    
     /* Description:search through the entire answers data set 
      * Input: void
      * Output: String
      */
    public static String search() {
        return search(0, answers.length, 0);
    }
    
    
     /*Description: search through answers within the range [top, bottom),
      * beginning with asking question 
      * Input: integer of starting index, ending index, and question to start
      * Output: String name of the animal guess
      */
    public static String search(int startingAnimalIdx, int endingAnimalIdx,
                                int questionIdx) {
        if (startingAnimalIdx + 1 == endingAnimalIdx) {
            return answers[startingAnimalIdx][0];
        }
        //Print Question
        System.out.println(questions[questionIdx]);
        String answer = Prompter.prompt("> ");
        
        //Calculate Split
        int split = findSplit(startingAnimalIdx, endingAnimalIdx, questionIdx);
        
        //Find Animal
        
        if (answer.equals("n")) {
            if (split != -1) {
                return search(startingAnimalIdx, split, questionIdx + 1);
            }
            else {
                return search(startingAnimalIdx, endingAnimalIdx, 
                              questionIdx + 1);
            }
        }
        else {
            if (split != -1) {
                return search(split, endingAnimalIdx, questionIdx + 1);
            }
            else {
                return search(startingAnimalIdx, endingAnimalIdx, 
                              questionIdx + 1);
            }
        }
        
    }
    
    /* Description: User Interaction code to play game
     * Input: filename
     * Output: void
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage:  java GuessingGame <FILENAME>");
        }
        else {
            readData(args[0]);
            sort();
            print(answers, questions);
            System.out.println(search());
        }
    }
}