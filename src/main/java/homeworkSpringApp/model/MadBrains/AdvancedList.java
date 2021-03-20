package homeworkSpringApp.model.MadBrains;

import java.util.Comparator;

public interface AdvancedList<T> extends SimpleList<T> {
    AdvancedList<T> shuffle();
    AdvancedList<T> sort(Comparator<T> comparator);
}
