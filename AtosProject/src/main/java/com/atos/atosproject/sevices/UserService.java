package com.atos.atosproject.sevices;

import com.atos.atosproject.entities.UserEntity;
import com.atos.atosproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.InvalidObjectException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {
@Autowired
        private UserRepository userRepository;

    Date  currentDate= Date.valueOf(LocalDate.now());
    private Object AgeCalculator;


    // dispaly details of  all users
    public Iterable<UserEntity> findAll() {
            return userRepository.findAll();
        }


    // dispaly details of users by  nom or country
        public Iterable<UserEntity> findAll( String search ) {
            if( search != null && search.length() > 0 ){
                return userRepository.findByNameContainsOrCountryContains( search , search );
            }
            return userRepository.findAll();
        }


// dispaly details of user by id
        public UserEntity findUser(int id) {
            return userRepository.findById(id).get();
        }






// check user email ( a verifier )
        public static boolean validateEmail(String emailStr) {
            Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();
        }

// calculate user's age
    public Date getAdultAge( Date birthDate) {

        return Date.valueOf(birthDate.toLocalDate().plusYears(18));
    }



    // check user email ( a verifier )
    public static boolean validatePhoneNumber(String phone) {
        String str = "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?";
      return  Pattern.compile(str).matcher(phone).matches();

    }
    // check  user attributes
        private void checkUser( UserEntity user ) throws InvalidObjectException {

            // check  name
            Pattern VALID_NAME=Pattern.compile("[A-Za-z]{3,25}",Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_NAME.matcher(user.getName());
           // return matcher.find();
            if (!matcher.find()) {
                throw new InvalidObjectException(" invalide Name");
            }
            
            // check country
System.out.println(user.getCountry().toLowerCase());
            if (user.getCountry().length() < 3 ) {
                throw new InvalidObjectException(" invalide Country");
            }else {
                if (!(user.getCountry().toUpperCase().equals("FRANCE"))) {
                    throw new InvalidObjectException(" You aren't allowed to create an account ");
                }
            }


            //check birthdate

            if (user.getBirthdate().after(currentDate)) {
                throw new InvalidObjectException(" invalide Birthdate ");
            }else {
                if (getAdultAge(user.getBirthdate()).after(currentDate)) {
                    throw new InvalidObjectException(" You aren't allowed to create an account ");
                }
            }




            if (user.getEmail().length() <= 5 || !validateEmail(user.getEmail())) {
                throw new InvalidObjectException("invalide Email");
            }


            if ( user.getPhoneNumber()!= null && validatePhoneNumber(user.getPhoneNumber())==false) {
                throw new InvalidObjectException("invalide PhoneNumber");
            }


        }


        //  add user
        public void addUser(UserEntity user) throws InvalidObjectException {
            checkUser(user);
            userRepository.save(user);
        }



        public void editUser(int id, UserEntity user) throws InvalidObjectException {
            checkUser(user);


            try{
                UserEntity userExistant = userRepository.findById(id).get();

                userExistant.setName( user.getName());
                userExistant.setBirthdate( user.getBirthdate() );
                userExistant.setCountry( user.getCountry() );
                userExistant.setPhoneNumber( user.getPhoneNumber() );
                userExistant.setGender( user.getGender() );
                userExistant.setEmail( user.getEmail() );
                userExistant.setPassword( user.getPassword() );

                userRepository.save( userExistant );

            }catch ( NoSuchElementException e ){
                throw e;
            }
        }


    public void delete(int id) {
        userRepository.deleteById(id);
    }

    }
