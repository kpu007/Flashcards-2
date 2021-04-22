package k07.flashcards2;

import javax.swing.text.*;

//http://java-sl.com/tip_center_vertically.html
public class VerticallyCenteredEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new StyledViewFactory();
    }

    static class StyledViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new CenteredBoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            return new LabelView(elem);
        }

    }
}

class CenteredBoxView extends BoxView {

    public CenteredBoxView(Element elem, int axis) {
        super(elem, axis);
    }

    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMajorAxis(targetSpan, axis, offsets, spans);
        int textBlockHeight = 0;
        int offset = 0;

        for (int i = 0; i < spans.length; i++) {
            textBlockHeight = spans[i];
        }

        offset = (targetSpan - textBlockHeight) / 2;
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] += offset;
        }
    }
}