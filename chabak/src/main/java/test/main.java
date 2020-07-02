package test;

public class main {
    public static void main(String[] args) {
        String id = "gnszja20";

        System.out.println(fakeId(id));

    }

    public static String fakeId(String id){
        String str1 = id.substring(0,3);
        String star = "";
        for (int i = 0; i < (id.length() - 3); i++) {
            star += "*";
        }

        return str1 + star;
    }
}
