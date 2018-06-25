import org.apache.commons.cli.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.cli.DefaultParser;

public class HelloSauceLabs
{
	public static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	public static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	public static String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub";

	public static String SELENIUM_PLATFORM = System.getenv("SELENIUM_PLATFORM");
	public static String SELENIUM_BROWSER = System.getenv("SELENIUM_BROWSER");
	public static String SELENIUM_VERSION = System.getenv("SELENIUM_VERSION");

	public static void main(String[] args) throws MalformedURLException
	{
		getOptions(args);

		URL url = new URL(SAUCE_URL.replace("SAUCE_USERNAME", SAUCE_USERNAME).replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY));
		System.out.println("URL: " + url);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", SELENIUM_PLATFORM);
		capabilities.setCapability("browserName", SELENIUM_BROWSER);
		capabilities.setCapability("version", SELENIUM_VERSION);
		System.out.println("CAPABILITIES: " + capabilities);

		RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);
		System.out.println("DRIVER: " + driver);

		driver.get("https://saucelabs.com/");

		String title = driver.getTitle();

		System.out.println(title);

		driver.quit();
	}
	public static void getOptions(String[] args)
	{
		Options options = new Options();
		Option username = new Option("u", "username", true, "SAUCE_USERNAME");
		options.addOption(username);
		Option accesskey = new Option("k", "accesskey", true, "SAUCE_ACCESS_KEY");
		options.addOption(accesskey);
		Option platform = new Option("p", "platform", true, "SELENIUM_PLATFORM");
		options.addOption(platform);
		Option browser = new Option("b", "browser", true, "SELENIUM_BROWSER");
		options.addOption(browser);
		Option version = new Option("v", "version", true, "SELENIUM_VERSION");
		options.addOption(version);


		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);

			if (cmd.getOptionValue("username") != null)
			{
				SAUCE_USERNAME = cmd.getOptionValue("username");
			}

			if (cmd.getOptionValue("accesskey") != null)
			{
				SAUCE_ACCESS_KEY = cmd.getOptionValue("accesskey");
			}

			if (cmd.getOptionValue("platform") != null)
			{
				SELENIUM_PLATFORM = cmd.getOptionValue("platform");
			}

			if (cmd.getOptionValue("browser") != null)
			{
				SELENIUM_BROWSER = cmd.getOptionValue("browser");
			}

			if (cmd.getOptionValue("version") != null)
			{
				SELENIUM_VERSION = cmd.getOptionValue("version");
			}
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
			formatter.printHelp("hellosaucelabs", options);
			System.exit(1);
		}

		if (SELENIUM_PLATFORM == null)
		{
			SELENIUM_PLATFORM = "Windows 10";
		}

		if (SELENIUM_BROWSER == null)
		{
			SELENIUM_BROWSER = "MicrosoftEdge";
		}

		if (SELENIUM_VERSION == null)
		{
			SELENIUM_VERSION = "latest";
		}

		if (SAUCE_USERNAME == null)
		{
			System.out.println("SAUCE_USERNAME must be set");
			System.exit(1);
		}

		if (SAUCE_ACCESS_KEY == null)
		{
			System.out.println("SAUCE_ACCESS_KEY must be set");
			System.exit(1);
		}
	}
}
