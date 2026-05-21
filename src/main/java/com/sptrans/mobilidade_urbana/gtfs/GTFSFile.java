package com.sptrans.mobilidade_urbana.gtfs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GTFSFile {
	
	String value();

}
