package org.courseworks.ris.widgets.viewers.finders;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Класс для поиска связных объектов<br>
 * На текущий момент аналогичен StringFinder
 */
public class RelatedObjectFinder extends AbstractFinder {

    /** Поле ввода значений */
    protected Text text;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public RelatedObjectFinder(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        label.setText(SC.SEARCH_HEADER);

        text = new Text(container, SWT.SINGLE);
        text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getSearchValue() {
        return text.getText();
    }
}
