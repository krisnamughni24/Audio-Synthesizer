package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHelper
{

	private FileHelper()
	{
		// hide public constructor
	}

	public static void writeFile(File file, String content)
	{
		try (FileWriter fileWriter = new FileWriter(file))
		{
			fileWriter.write(content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String readFile(File file)
	{
		try (Scanner scanner = new Scanner(file))
		{
			StringBuilder stringBuilder = new StringBuilder();
			while (scanner.hasNextLine())
			{
				stringBuilder.append(scanner.nextLine());
			}
			return stringBuilder.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
