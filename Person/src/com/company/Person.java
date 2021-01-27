package com.company;

import java.io.*;

public class Person {

    private String username;
    private GameController gc= new GameController();

    public Person(String username,GameController gc)
    {
        this.username=username;
        this.gc=gc;
    }


    public void saveGame()
    {

        File gcFile=new File("./Users/%s.obj",username);

        try {
            FileOutputStream fis= new FileOutputStream(gcFile);
            ObjectOutputStream oos=new ObjectOutputStream(fis);
            oos.writeObject(gc);
            oos.close();
            fis.close();
            }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadGame()
    {

        File gcFile=new File("./Users/%s.obj",username);
        try {

            FileInputStream fis=new FileInputStream(gcFile);
            BufferedInputStream bis=new BufferedInputStream(fis);
            ObjectInputStream ois=new ObjectInputStream(bis);
            gc= (Gamecontroller) ois.read();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
