package com.codepuppies.danmuji.ui;

import org.apache.commons.cli.*;

/**
 * 命令行解析
 *
 * @author Sumy
 * @version 2016-3-9
 */
public class CommandLineHandler {
    private Options options = new Options();
    private CommandLine cmd;

    private boolean isContinue = true; //命令行是否提供足够的数据
    private int liveid;

    public CommandLineHandler() {
        options.addOption("h", "help", false, "显示此帮助");
        options.addOption(Option.builder("i").required().longOpt("liveid").hasArg().argName("id").desc("【必须】直播房间号，数字").build());
    }

    public void parse(final String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                //打印帮助，退出
                showHelp();
                isContinue = false;
                return;
            }
            if (cmd.hasOption("i")) {
                try {
                    liveid = Integer.parseInt(cmd.getOptionValue("i"));
                } catch (NumberFormatException e) {
                    isContinue = false;
                    System.out.println("liveid格式错误");
                    showHelp();
                }
            }
        } catch (ParseException e) {
            //解析失败，打印帮助，不再继续
            isContinue = false;
            showHelp();
        }
    }

    public void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("danmuji -i <id> [OPTION]", options);
    }

    public boolean canContinue() {
        return isContinue;
    }

    public int getLiveid() {
        return this.liveid;
    }
}
