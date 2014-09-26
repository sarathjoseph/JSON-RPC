package rit756;

interface JDBMethodsInterface {

	public boolean selectOnId(Contact contact);

	public boolean selectOnLastName(Contact contact);

	public boolean update(Contact contact);

	public boolean insert(Contact contact);

	public boolean delete(Contact contact);
}