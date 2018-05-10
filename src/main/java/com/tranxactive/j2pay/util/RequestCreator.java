package com.tranxactive.j2pay.util;

import com.tranxactive.j2pay.gateways.impl.authorize.AuthorizeGateway;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class RequestCreator {

	static Configuration cfg;

	private RequestCreator() {

	}

	static {
		cfg = new Configuration();
		cfg.setClassForTemplateLoading(AuthorizeGateway.class, "/templates");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public static String createRequest(final Map<String, Object> input, final String templatePath) {
		String request = null;
		try (final StringWriter stringWriter = new StringWriter()){
			final Template template = cfg.getTemplate(templatePath);
			template.process(input, stringWriter);
			request = stringWriter.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return request;
	}


}
