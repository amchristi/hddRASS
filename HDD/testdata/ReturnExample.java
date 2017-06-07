public class ReturnExample {

    public String getUndoOrRedoPresentationName() {
        if (indexOfNextAdd == edits.size()) {
            return getUndoPresentationName();
        } else {
            return getRedoPresentationName();
        }
    }

    private String getUndoPresentationName(){
        return "abcd";
    }

    private String getRedoPresentationName(){
        return "efgh";
    }
}