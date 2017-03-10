package utils;

import ezvcard.VCard;
import ezvcard.parameter.ImageType;
import ezvcard.property.Address;
import ezvcard.property.Birthday;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import objects.Contact;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VcardFactory {

    public static VCard createVcard (Contact contact) {
        VCard newVcard = new VCard();

        StructuredName name = new StructuredName();
        name.setFamily(contact.getSurname());
        name.setGiven(contact.getName());
        name.addAdditional(contact.getMiddleName());
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

    public static Contact parseVcard (VCard vCard) {
        Contact newContact = new Contact();
        StructuredName structuredName = vCard.getStructuredName();
        if (structuredName.getFamily() == null && structuredName.getGiven() == null && structuredName.getAdditional().size() == 0) {
            return null;
        }
        newContact.setSurname(structuredName.getFamily() == null ? structuredName.getGiven() : structuredName.getFamily());
        newContact.setName(structuredName.getGiven() == null ? "" : structuredName.getGiven());
        newContact.setMiddleName(structuredName.getAdditional().size() == 0 ? "" : structuredName.getAdditional().get(0));

        if (vCard.getTelephoneNumbers() != null) {
            newContact.setPhoneNumber(vCard.getTelephoneNumbers().size() == 0 ? "" : vCard.getTelephoneNumbers().get(0).getText());
        }
        if (vCard.getEmails() != null) {
            newContact.setEmail(vCard.getEmails().size() == 0 ? "" : vCard.getEmails().get(0).getValue());
        }
        List<Address> address = vCard.getAddresses();
        if (address.size() > 0) {
            newContact.setCountry(address.get(0).getCountry());
            newContact.setCity(address.get(0).getLocality());
            newContact.setAddress(address.get(0).getStreetAddress());
        }
        Date date = vCard.getBirthday() == null ? null : vCard.getBirthday().getDate();
        if (date != null) {
            String birthday = ConvertData.convertLocalDateToString((date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
            newContact.setBirthday(birthday);
        }
        if (vCard.getNotes() != null) {
            newContact.setPersonNote(vCard.getNotes().size() == 0 ? "" : vCard.getNotes().get(0).getValue());
        }
        if (vCard.getPhotos() != null) {
            byte[] photo = vCard.getPhotos().size() == 0 ? null : vCard.getPhotos().get(0).getData();
            newContact.setPersonImage(photo);
        }
        return newContact;
    }
}
