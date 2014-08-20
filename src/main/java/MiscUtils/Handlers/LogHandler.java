package MiscUtils.Handlers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {

    int Lv;
    String Nm;

    public LogHandler(String LogName, int LevelOfLogging){
        Nm = LogName;
        Lv = LevelOfLogging;
    }

    public final Logger LOGGER = LogManager.getLogger(Nm);


    public void OutputMessage(String Text, Level level, int Priority){
        if(Priority <= Lv)
        LOGGER.log(level, Text);
    }

    public void Error(String Text, int Priority){
        if(Priority <= Lv)
            LOGGER.log(Level.ERROR, Text);
    }

    public void Debug(String Text, int Priority){
        if(Priority <= Lv)
            LOGGER.log(Level.DEBUG, Text);
    }

    public void All(String Text, int Priority){
        if(Priority <= Lv)
            LOGGER.log(Level.ALL, Text);
    }


    public void Error(String Text){
            LOGGER.log(Level.ERROR, Text);
    }

    public void Debug(String Text){
            LOGGER.log(Level.DEBUG, Text);
    }

    public void All(String Text){
            LOGGER.log(Level.ALL, Text);
    }


}
