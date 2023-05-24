package com.example.captaincat.Utils;

import static com.example.captaincat.Ui.Dialog.OrcDialog.showOrcInfo;
import static com.example.captaincat.information.Tables.ObjectTable.getThing;

import android.content.Context;

import com.example.captaincat.Ui.Dialog.DialogItem;
import com.example.captaincat.flyingObjects.Orcs.Orc;
import com.example.captaincat.flyingObjects.Orcs.Orc1;
import com.example.captaincat.flyingObjects.Orcs.Orc2;
import com.example.captaincat.flyingObjects.Orcs.Orc3;
import com.example.captaincat.flyingObjects.Orcs.Orc4;
import com.example.captaincat.flyingObjects.Orcs.Orc5;
import com.example.captaincat.flyingObjects.Orcs.Orc6;
import com.example.captaincat.flyingObjects.Orcs.Orc7;
import com.example.captaincat.flyingObjects.Orcs.Orc8;
import com.example.captaincat.flyingObjects.Orcs.Orc9;
import com.example.captaincat.model.Thing;

import java.util.ArrayList;
import java.util.List;

public class ListTurner {
    
    public  static List<DialogItem> orcsToDialogItem(int level, Context context){
        List<Orc> orcs = new ArrayList<>();
        orcs.add(new Orc1(context));
        orcs.add(new Orc2(context));
        orcs.add(new Orc3(context));
        orcs.add(new Orc4(context));
        orcs.add(new Orc5(context));
        orcs.add(new Orc6(context));
        orcs.add(new Orc7(context));
        orcs.add(new Orc8(context));
        orcs.add(new Orc9(context));
            List<DialogItem> itemList = new ArrayList<>();
        for (Orc orc :orcs) {
            itemList.add(new DialogItem(orc.getName(), orc.getLook() ,v -> {
                showOrcInfo(context,orc);
            }));
        }
        int len;
        if(level<3)
            len = 3;
        else if (level<9)
                len = 5;
        else if (level<13)
            len = 6;
        else len = 8 ;

        return itemList.subList(0,len+1);
        
        
    }

    /**
     * 后端传来的json是根据id排序后的全部物品列表，id及下标，0代表金币*/
    public static List<Thing> arrayToThings(int[][] t){
        ArrayList<Thing> list = new ArrayList<>();
        for (int[] i : t) {
            Thing thing = getThing(i[0]);
            thing.setNum(i[1]);
            if(i[1]>0)
            list.add(thing);
        }
        return list;

    }
}
