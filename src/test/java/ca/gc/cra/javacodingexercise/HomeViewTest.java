package ca.gc.cra.javacodingexercise;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.testng.Assert;

public class HomeViewTest {
    
      HomeView homeView;
    
      
      @BeforeClass
      public void beforeClass() {
          homeView = new HomeView();
      }
      
      @AfterClass
      public void afterClass() {
          homeView = null;
      }

      @BeforeTest
      public void beforeTest() {
          if(homeView == null) {
              homeView = new HomeView();
          }
      }

      @AfterTest
      public void afterTest() {
          if(homeView == null) {
              homeView = new HomeView();
          }
      }
      
      @Test(enabled=true) 
      public void validateTextFieldTest() {
          JTextField tanField = new JTextField(Settings.FIELD_LENGTH);
          tanField.setName("TAN");
          tanField.setText("T12345678");       

          JTextField provinceField = new JTextField(Settings.FIELD_LENGTH);
          provinceField.setName("Province");
          provinceField.setText("A invalid province");    

          JTextField piField = new JTextField(Settings.FIELD_LENGTH);
          piField.setName("PI");
          piField.setText("3"); // valid input value, so shall be return an empty string as error message.
          
          Assert.assertEquals(homeView.validateTextField(tanField), "");
          Assert.assertTrue("Province must be a Canadian province.".equals(homeView.validateTextField(provinceField)));
          Assert.assertFalse(homeView.validateTextField(provinceField).length() == 0);
      }
      
      @Test(enabled=true) 
      public void readCsvFileTest() {
          String invalidPath = "c:\\a path is never exist on my disk, and god bless you if you really want to locate it!";
          JFileChooser fileChooser = new JFileChooser(invalidPath);
          Object csvContents = homeView.readCsvFile(fileChooser); // should be null, cos' the file path is not exist.
          Assert.assertNull(csvContents);
      }

      @Test(enabled=true) 
      public void myAssertEqualsTest() {
          JTextField provinceField = new JTextField(Settings.FIELD_LENGTH);
          provinceField.setName("Province");
          provinceField.setText("On"); // case insensitive. 
          Assert.assertTrue("".equals(homeView.validateTextField(provinceField)));
      }
      
      @Test(enabled=true) 
      public void myAssertNotEqualsTest() {
          JTextField piField = new JTextField(Settings.FIELD_LENGTH);
          piField.setName("PI");
          piField.setText("3.5"); // invalid input: should be an Integer.   
          Assert.assertTrue("Please enter an integer for PI.".equals(homeView.validateTextField(piField)));
      }
      
      @Test(enabled=true) 
      public void myAssertTrueTest() {
          JTextField piField = new JTextField(Settings.FIELD_LENGTH);
          piField.setName("PI");
          piField.setText("-3"); // invalid input: should be a positive Integer.    
          Assert.assertTrue("Please enter a positive integer for PI.".equals(homeView.validateTextField(piField)));
      }
      
      @Test(enabled=false) 
      public void myAssertFalseTest() {
          JTextField tanField = new JTextField(Settings.FIELD_LENGTH);
          tanField.setName("TAN");
          tanField.setText("a1234567"); // invalid input: should start with T/t.    
          Assert.assertFalse(homeView.validateTextField(tanField).length() == 0);//this is designed to fail.
      }
    
      @Test(enabled=true)
      public void testTanField() {
    	  JTextField tanField = new JTextField(Settings.FIELD_LENGTH);
    	  tanField.setName("TAN");
    	  tanField.setText("A12345678"); // invalid: should start with T.    
          Assert.assertTrue("TAN must start with T.".equals(homeView.validateTextField(tanField)));
     
    	  tanField.setText("T1234567"); // invalid: should contain 9 letters.    
          Assert.assertTrue("TAN must contain 9 letters.".equals(homeView.validateTextField(tanField)));
          
          tanField.setText("T123456789"); // invalid: should contain 9 letters.  
          Assert.assertTrue("TAN must contain 9 letters.".equals(homeView.validateTextField(tanField)));
     
          tanField.setText("T1234567a"); // invalid: should contain 8 digits.  
          Assert.assertTrue("Please enter 8 digits after T or t for TAN.".equals(homeView.validateTextField(tanField)));
     
          tanField.setText("T12345678"); // invalid: should contain 8 digits.  
          Assert.assertTrue("".equals(homeView.validateTextField(tanField)));
     
      }
      
    }
