package pw.ollie.sprint.collect;

import java.util.List;

/**
 * Represents a {@link List} which can be split up into different pages.
 *
 * @param <E> the type of element stored in the {@link List}
 */
public interface PagedList<E> extends List<E> {
    List<E> getPage(int page);

    int getElementsPerPage();

    void setElementsPerPage(int elementsPerPage);
}
