public class DictionaryItem {
    private int count;
    private String word;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public DictionaryItem(String word, int count){
        this.word = word;
        this.count = count;
    }

    @Override
    public String toString(){
        return this.word + ", " + this.count;
    }



}
