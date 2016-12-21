/**
 * Created by Confiz-234 on 12/21/2016.
 */
public class Resource {

    String file_url = "data.txt";

    public boolean verifyCredentials(String name, String pass) {
        return name.equals("Bilal") && pass.equals("12345");
    }
}
