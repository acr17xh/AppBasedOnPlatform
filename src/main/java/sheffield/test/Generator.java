/*    */ package sheffield.test;

/*    */
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;

/*    */ import org.mybatis.generator.api.MyBatisGenerator;
/*    */ import org.mybatis.generator.config.Configuration;
/*    */ import org.mybatis.generator.config.xml.ConfigurationParser;
/*    */ import org.mybatis.generator.exception.InvalidConfigurationException;
/*    */ import org.mybatis.generator.exception.XMLParserException;
/*    */ import org.mybatis.generator.internal.DefaultShellCallback;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class Generator
/*    */ {
	/*    */ public static void main(String[] args)/*    */ throws IOException, XMLParserException,
			InvalidConfigurationException, SQLException, InterruptedException
	/*    */ {
		/* 26 */ List<String> warnings = new ArrayList();
		/* 27 */ boolean overwrite = true;
		/* 28 */ InputStream is = Generator.class.getResourceAsStream("/mybatis/generator-config.xml");
		/* 29 */ ConfigurationParser cp = new ConfigurationParser(warnings);
		/* 30 */ Configuration config = cp.parseConfiguration(is);
		/* 31 */ is.close();
		/* 32 */ DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		/* 33 */ MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		/* 34 */ myBatisGenerator.generate(null);
		/* 35 */ for (String warning : warnings) {
			/* 36 */ System.out.println(warning);
			/*    */ }
		/*    */ }
	/*    */ }
