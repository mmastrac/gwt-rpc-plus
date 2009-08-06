#!/bin/sh
../../../../../../../../libs/javacc/javacc-4.2/bin/jjtree thrift_grammar.jjt
../../../../../../../../libs/javacc/javacc-4.2/bin/javacc -LOOKAHEAD:2 thrift_grammar.jj
