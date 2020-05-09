# Nested Language Escape Bypassing Demo

A simple demonstration project in Java demonstrating why developers should be careful about combining escaping with
nesting languages.

Escaping can be escaping SQL in JDBC queries, HTML-escaping values in server-side templating, or many other scenarios.

Nesting languages means anywhere in which an escaping system is applied on top of a language that itself supports
nesting inner languages. The quintessential example of this is `<script>` within HTML templates.

This repository uses FreeMarker and JS injections for its example, and uses
[JSFuck](http://www.jsfuck.com) to obfuscate JS payloads in one of the examples.

The code in this repository is automatically formatted with
[google-java-format](https://github.com/google/google-java-format).

This repository is hosted [on
GitLab.com](https://gitlab.com/louis.jackman/freemarker-nested-template-injection-example).
If you're seeing this on GitHub, you're on the official GitHub mirror. [Go to
GitLab](https://gitlab.com/louis.jackman/freemarker-nested-template-injection-example)
to contribute.

