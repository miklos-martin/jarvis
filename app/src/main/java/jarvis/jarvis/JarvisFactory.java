package jarvis.jarvis;

public class JarvisFactory {
    public static Jarvis create() {
        Util util = new Util();
        Jarvis jarvis = new Jarvis();
        jarvis.add(new ChuckNorris(util));
        jarvis.add(new GreetingsHuman());
        jarvis.add(new HowDoI(util));

        return jarvis;
    }
}
