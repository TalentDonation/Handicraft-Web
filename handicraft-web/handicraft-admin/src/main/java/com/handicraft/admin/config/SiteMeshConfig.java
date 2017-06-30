package com.handicraft.admin.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteMeshConfig extends ConfigurableSiteMeshFilter{

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		// TODO Auto-generated method stub
		super.applyCustomConfiguration(builder);
		
		builder.addDecoratorPath("/*", "/decoreators/decorator.html")
				.addExcludedPath("/login");
		
		builder.addTagRuleBundles(new Sm2TagRuleBundle());
	}



}
