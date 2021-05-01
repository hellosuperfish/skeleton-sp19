public class NBody {
    public static double readRadius(String fileName){
        In in = new In(fileName);

        int planetNum = in.readInt();
        double radius = in.readDouble();

        return radius;
    }

    public static Body[] readBodies(String fileName){
        In in = new In(fileName);

        int planetNum = in.readInt();
        double radius = in.readDouble();

        Body[] planets = new Body[planetNum];

        for(int i = 0; i < planetNum; i++){

            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String imgName = in.readString();
            planets[i] = new Body(xPos, yPos, xVel, yVel, mass, imgName);

        }

        return planets;
    }


    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        double universeRad = NBody.readRadius(fileName);
        Body[] planets = NBody.readBodies(fileName);


        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-universeRad, universeRad);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();


        for(int i = 0; i < planets.length; i++){
            planets[i].draw();
        }

        double time = 0.0;
        while(time <= T){
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            // update forces for each planet, stored in two arrays
            for(int i = 0; i < planets.length; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            // update position and velocity based on forces calculated above
            for(int i = 0; i < planets.length; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            //StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(int i = 0; i < planets.length; i++){
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time = time + dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", universeRad);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
