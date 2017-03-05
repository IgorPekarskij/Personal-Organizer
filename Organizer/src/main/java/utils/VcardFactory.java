package utils;

import ezvcard.VCard;
import ezvcard.parameter.ImageType;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import objects.Contact;
import java.time.LocalDate;
import java.util.Calendar;

public class VcardFactory {

    public static VCard createVcard (Contact contact) {
        VCard newVcard = new VCard();

        StructuredName name = new StructuredName();
        name.setFamily(contact.getSurname());
        name.setGiven(contact.getName() + " " + contact.getMiddleName());
        newVcard.setStructuredName(name);

        newVcard.setFormattedName(contact.getSurname() + " " + contact.getName() + " " + contact.getMiddleName());
        Address address = new Address();
        address.setStreetAddress(contact.getAddress());
        address.setLocality(contact.getCity());
        address.setCountry(contact.getCountry());
        newVcard.addAddress(address);

        newVcard.addTelephoneNumber(contact.getPhoneNumber());
        newVcard.addEmail(contact.getEmail());
        newVcard.addNote(contact.getPersonNote());

        LocalDate localDate = ConvertData.convertStringToLocalDate(contact.getBirthday());
        if (localDate != null) {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(Calendar.YEAR, localDate.getYear());
            c.set(Calendar.MONTH, localDate.getMonthValue());
            c.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
            Birthday birthday = new Birthday(c.getTime());
            newVcard.setBirthday(birthday);
        }
        Photo photo = new Photo(contact.getPersonImage(), ImageType.JPEG);
        newVcard.addPhoto(photo);

        return newVcard;
    }
}
