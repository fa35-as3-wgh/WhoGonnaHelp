package fa35.group2.view.tui;

import fa35.group2.view.tui.prints.PrintHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TuiHelper
{
    private BufferedReader reader;

    private PrintHelper printHelper;

    public TuiHelper()
    {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.printHelper = new PrintHelper();
    }

    public String readLine()
    {
        String line;
        try {
            if ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Integer readLineExpectInteger()
    {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    return null;
                }
                try {
                    return Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    this.printHelper.printWrongInput();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
