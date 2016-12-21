/**
 * Created by Confiz-234 on 12/21/2016.
 */
public class Gatekeeper {
    Resource resource;

    public Gatekeeper() {
        resource = new Resource();
    }

    public boolean verifyCredentials(String name, String pass) {
        boolean result = resource.verifyCredentials(name, pass);
        return result;
    }

    public String getFileUrL() {
        return resource.file_url;
    }
}
