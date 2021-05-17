import es.datastructur.synthesizer.Harp;

public class HarpHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static double[] concert = new double[keyboard.length()];


    public static void main(String[] args) {
        Harp[] string = new Harp[keyboard.length()];

        // fill the keyboard;
        for (int i = 0; i < keyboard.length(); i++) {
            concert[i] = 440 * Math.pow(2, (i - 24) / 12.0);
            string[i] = new Harp(concert[i]);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index == -1) {
                    continue;
                }
                string[index].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < string.length; i++) {
                sample += string[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < string.length; i++) {
                string[i].tic();
            }
        }

    }


}
