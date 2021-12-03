package com.example.littledinosaur;



import java.util.ArrayList;
import java.util.List;

public class ListLikesAndCollects {
    public static List<String> likes = new ArrayList<>();
    public static List<String> collects = new ArrayList<>();
    public static List<String> notes = new ArrayList<>();

    public static void addLike(String a){
        likes.add(a);
    }
    public static void removeLike(String a){
        likes.remove(a);
    }
    public static void addCollect(String a){
        collects.add(a);
    }
    public static void removeCollect(String a){
        collects.remove(a);
    }
    public static void addnotes(String a){
        notes.add(a);
    }
    public static void removenotes(String a){
        notes.remove(a);
    }
    public static void clearList(){
        likes.clear();
        collects.clear();
        notes.clear();
    }
}
