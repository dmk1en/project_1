package project.demo;

import project.scan.urlScan;

public class App 
{
    public static void main( String[] args )
    {
    	urlScan a = new urlScan("https://133806.com/?home=casino&a=x");
    	a.scan();
    }
}