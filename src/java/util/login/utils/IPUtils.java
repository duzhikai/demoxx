package util.login.utils;

import cn.hutool.core.util.StrUtil;
import org.example.util.config.EnvConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {

    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if(ipAddress!=null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
            ipAddress = StrUtil.sub(ipAddress, 0, 15);
        }
        return ipAddress;
    }
    /**
     * 获取mac地址
     * @param ip
     * @return
     */
    public static String getMacAddress(String ip){
        String macAddress = "";

        if (EnvConfig.DEV_ENV.equals(EnvConfig.env)) {
            return macAddress;
        }

        //本地跑不起来可先注释调
        macAddress = getMacInWindows(ip).trim();
        if(macAddress==null||"".equals(macAddress)){
            macAddress = getMacInLinux(ip).trim();
        }
        return macAddress;
    }

    public static String getMacInWindows(final String ip){
        String result = "";
        String[] cmd = {
                "cmd",
                "/c",
                "ping " + ip
        };
        String[] another = {
                "cmd",
                "/c",
                "arp -a"
        };
        String cmdResult = callCmd(cmd,another);
        result = filterMacAddress(ip,cmdResult,"-");
        return result;
    }
    /**
     * 获取linux的mac
     * @param ip 目标ip
     * @return Mac Address
     *
     */
    public static String getMacInLinux(final String ip){
        String result = "";
        String[] cmd = {
                "/bin/sh",
                "-c",
                "ping " + ip + " -c 2 && arp -a"
        };
        String cmdResult = callCmd(cmd);
        result = filterMacAddress(ip,cmdResult,":");
        return result;
    }

    /**
     * 执行第一个命令
     * @param cmd
     * @return
     */
    public static String callCmd(String[] cmd) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);// ignore_security_alert
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * @param cmd 第一个命令
     * @param another 第二个命令
     * @return 第二个命令的执行结果
     */
    public static String callCmd(String[] cmd,String[] another) {
        String result = "";
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);// ignore_security_alert
            proc.waitFor(); //已经执行完第一个命令，准备执行第二个命令
            proc = rt.exec(another);// ignore_security_alert
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param ip 目标ip,一般在局域网内
     * @param sourceString 命令处理的结果字符串
     * @param macSeparator mac分隔符号
     * @return mac地址，用上面的分隔符号表示
     */
    public static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while(matcher.find()){
            result = matcher.group(1);
            if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                break; //如果有多个IP,只匹配本IP对应的Mac.
            }
        }
        return result;
    }
}
