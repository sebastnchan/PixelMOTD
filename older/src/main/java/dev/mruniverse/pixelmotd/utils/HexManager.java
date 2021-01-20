package dev.mruniverse.pixelmotd.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexManager {
    private boolean hexStatus = false;
    private final Pattern HEX = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    private final Pattern GRADIENT = Pattern.compile("<gradient: #([A-Fa-f0-9]){6}-#([A-Fa-f0-9]){6}:[^%]*>");
    public String applyColor(String message) {
        if(hexStatus) {
            String stepF,stepS;
            stepF = "";
            stepS = "";
            Matcher normalHex, gradientHex;
            normalHex = HEX.matcher(message);
            while (normalHex.find()) {
                stepF = message.substring(0, normalHex.start());
                stepS = message.substring(normalHex.end());
                message = stepF + ChatColor.of(normalHex.group().substring(1, 8)) + stepS;
                normalHex = HEX.matcher(message);
            }
            gradientHex = GRADIENT.matcher(message);
            while (gradientHex.find()) {
                int redF,redS,greenF,greenS,blueF,blueS,join;
                String starter,finisher,gradient;
                String code1,code2,code3,code4,code5;
                code1 = "";
                code2 = "";
                code3 = "";
                code4 = "";
                code5 = "";
                gradient = gradientHex.group();
                starter = gradient.substring(11,18);
                finisher = gradient.substring(19,26);
                redF = Integer.valueOf(starter.substring(1, 3), 16);
                redS = Integer.valueOf(finisher.substring(1, 3), 16);
                greenF = Integer.valueOf(starter.substring(3, 5), 16);
                greenS = Integer.valueOf(finisher.substring(3, 5), 16);
                blueF = Integer.valueOf(starter.substring(5, 7), 16);
                blueS = Integer.valueOf(finisher.substring(5, 7), 16);
                gradient = gradient.substring(27, gradient.length() - 1);
                if (gradient.length() > 3) {
                    if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                        code1 = color("§" + gradient.substring(gradient.length() - 1));
                        gradient = gradient.substring(0, gradient.length() - 3);
                        if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                            code3 = color("§" + gradient.substring(gradient.length() - 1));
                            gradient = gradient.substring(0, gradient.length() - 3);
                            if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                                code2 = color("§" + gradient.substring(gradient.length() - 1));
                                gradient = gradient.substring(0, gradient.length() - 3);
                                if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                                    code3 = color("§" + gradient.substring(gradient.length() - 1));
                                    gradient = gradient.substring(0, gradient.length() - 3);
                                    if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                                        code4 = color("§" + gradient.substring(gradient.length() - 1));
                                        gradient = gradient.substring(0, gradient.length() - 3);
                                        if (gradient.substring(gradient.length() - 3).matches(";&[lkmon]")) {
                                            code5 = color("§" + gradient.substring(gradient.length() - 1));
                                            gradient = gradient.substring(0, gradient.length() - 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                char[] gradientTextLetters = gradient.toCharArray();
                join = 0;
                double l = 1.0D / gradient.length();
                StringBuilder part = new StringBuilder();
                for (double i = 0.0D; i < 1.0D; i += l) {
                    if (join >= gradientTextLetters.length)
                        break;
                    int r = (int)(redF * (1.0D - i) + redS * i);
                    int g = (int)(greenF * (1.0D - i) + greenS * i);
                    int b = (int)(blueF * (1.0D - i) + blueS * i);
                    part.append(ChatColor.of(String.format("#%02X%02X%02X", r, g, b)).toString()).append(code1).append(code2).append(code3).append(code4).append(code5).append(gradientTextLetters[join]);
                    join++;
                }
                message = stepF + part + stepS;
                gradientHex = GRADIENT.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&',message);
    }
    public void setHex(boolean toggleHex) { hexStatus = toggleHex;}
    public boolean getStatus() { return hexStatus; }
    private String color(String message) { return message; }

}

