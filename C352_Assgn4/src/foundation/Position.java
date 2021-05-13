package foundation;
// Position interface
public interface Position<E>{
    /**
     * Returns the element stored at this postion.
     *
     * @return the stored element
     * @throws IllegalStateException if position no longer valid
     */
    E getElement() throws IllegalStateException;
}
