/*
 * NeuronAnimActivity.java
 *
 * Norbiron Game
 * This is a case study for creating sprites for SamuEntropy/Brainboard.
 *
 * Copyright (C) 2016, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * 0.0.1, 2013.szept.29.
 */
package batfai.samuentropy.brainboard8;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.io.*;
import java.util.List;

/**
 *
 * @author nbatfai
 */
public class NeuronGameActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loadNodes();
    }

    public void saveNodes()
    {
        List<NeuronBox> nodes = NorbironSurfaceView.getNodeBoxes();
        try
        {
            FileOutputStream fileOutputStream = openFileOutput("nodeSave", Context.MODE_PRIVATE);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            dataOutputStream.writeInt(nodes.size());
            for(NeuronBox node : nodes)
            {
                dataOutputStream.writeInt(node.getId());
                dataOutputStream.writeInt(node.getX());
                dataOutputStream.writeInt(node.getY());
            }
            dataOutputStream.flush();
            dataOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadNodes()
    {
        if(NorbironSurfaceView.getNodeBoxes().size() == 0)
        try
        {
            FileInputStream fileInputStream = openFileInput("nodeSave");
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            int nodeCount = dataInputStream.readInt();
            for(int i = 0; i < nodeCount; i++)
            {
                int id = dataInputStream.readInt();
                int x = dataInputStream.readInt();
                int y = dataInputStream.readInt();
                NorbironSurfaceView.createNode(id, x, y);
            }
            dataInputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        saveNodes();
    }
}
