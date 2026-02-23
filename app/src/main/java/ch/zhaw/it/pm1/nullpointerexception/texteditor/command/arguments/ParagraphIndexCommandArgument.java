package ch.zhaw.it.pm1.nullpointerexception.texteditor.command.arguments;

import ch.zhaw.it.pm1.nullpointerexception.texteditor.Text;

public class ParagraphIndexCommandArgument extends IntegerCommandArgument {
    public ParagraphIndexCommandArgument(Text text, int offset) {
        super(1, text.size() + offset);
    }

    public ParagraphIndexCommandArgument(Text text) {
        this(text, 0);
    }
}
