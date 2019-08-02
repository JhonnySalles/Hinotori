/**
 * @author Jhonny
 *
 */
module Comum {
	exports model.mask;
	exports model.constraints;
	exports model.cliping;
	exports model.animation;
	exports model.encode;
	exports model.config;
	exports mysql;

	requires java.logging;
	requires javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires java.naming;
	requires transitive java.sql;
}