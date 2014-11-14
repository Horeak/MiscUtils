package MiscUtils.Config;

import MiscUtils.MiscUtilsMain;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MiscUtilsConfig extends ConfigBase {

    public MiscUtilsConfig(String Loc){
        super(new Configuration(new File(Loc + "/MiscUtils mods/" + MiscUtilsMain.Name + ".cfg")));
        InitConfig();
    }


    @Override
    public void InitConfig() {
        LoadConfig();
    }

    @Override
    public void LoadConfig() {

        if(GetConfigFile().hasChanged())
            GetConfigFile().save();
    }
}
