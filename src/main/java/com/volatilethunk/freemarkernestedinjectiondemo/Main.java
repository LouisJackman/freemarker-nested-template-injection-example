package com.volatilethunk.freemarkernestedinjectiondemo;

import static java.lang.System.out;

import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

final class Main {

  /** Read a resource from the context of the current class into a string. */
  private static String readResource(String fileName) {
    var url = Main.class.getResource(fileName);
    var pathString = url.getFile();
    var path = Paths.get(pathString);
    try {
      var templateLines = Files.readAllLines(path);
      return String.join("\n", templateLines);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /** Create a FreeMarker configuration designed for HTML inputs. */
  private static Configuration configure() {
    var cfg = new Configuration(Configuration.VERSION_2_3_27);
    cfg.setDefaultEncoding("UTF-8");
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    cfg.setLogTemplateExceptions(false);
    cfg.setWrapUncheckedExceptions(true);
    cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
    return cfg;
  }

  /** Create the template model with the payload demonstration. */
  private static Map<String, Object> createModel(String payloadResourceName) {
    var evilValue = readResource(payloadResourceName);
    return Map.of("value", evilValue);
  }

  /** Display the injection results. */
  private static void displayResults(String name, Template template, Map<String, Object> model) {
    out.printf("-=[ Injection Case: %s ]=-%n%n", name);

    var output = new StringWriter();
    try {
      template.process(model, output);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }

    out.println(output.toString());
    out.println();
    out.println();
  }

  /** Demonstrate an injection case. */
  private static void demonstrate(
      Configuration configuration,
      String name,
      String templateName,
      String injectionPayloadResourceName) {
    try {
      var content = readResource(templateName);
      var template = new Template(name, new StringReader(content), configuration);
      displayResults(name, template, createModel(injectionPayloadResourceName));
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  public static void main(String[] main) {
    var cfg = configure();

    out.println(
        "This demo shows why nesting languages allows escaping to be bypassed by escaping the layers\n"
            + "different to the one actually being escaped. This example shows bypassing FreeMarker HTML\n"
            + "by injecting an inline script within.\n"
            + "\n"
            + "The moral of the story is to avoid nesting languages, such as JS within HTML within FreeMarker,\n"
            + "and to escape _all_ layers if it's absolutely necessary."
            + "\n");

    demonstrate(cfg, "Correctly Escaped Payload", "page.ftl", "html-injection-payload.txt");

    demonstrate(
        cfg,
        "Incorrectly Escaped Broken Payload",
        "page-with-inline-script.ftl",
        "js-injection-payload.txt");

    demonstrate(
        cfg,
        "Incorrectly Escaped Obfuscated, Working Payload",
        "page-with-inline-script.ftl",
        "obfuscated-js-injection-payload.txt");
  }
}
