package com.uniovi;

import org.junit.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1920Entrega1604607ApplicationTests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\thali_12wmf8x\\Documents\\Clase\\SDI\\Practica-Spring\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		driver.quit();
	}
	
	// Inicio de sesión con datos válidos (administrador).
	@Test
	public void prueba05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// TODO: comprobar que esta en la vista que lista todos los usuarios con opciones de administrador
		PO_View.checkElement(driver, "text", "usuarios");
	}

	// Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void prueba06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "usuario@email.com", "usuario");
		// TODO: comprobar que esta en la vista que lista todos los usuarios
		PO_View.checkElement(driver, "text", "usuarios");
	}
	
	// Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos)
	@Test
	public void prueba07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		// Comprobar que seguimos en la vista de login
		PO_View.checkElement(driver, "text", "Login");
	}
	
	//Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña
	//		incorrecta).
	@Test
	public void prueba08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "usuario@email.com", "contraseña");
		// Comprobar que ha salido el mensaje de error
		PO_View.checkElement(driver, "text", "Usuario o contraseña incorrectos.");
	}
	
	// Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio de
	// sesión (Login)
	@Test
	public void prueba09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobar que estamos en la vista de login
		PO_View.checkElement(driver, "text", "Password");
	}
	
	// Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado
	@Test
	public void prueba10() {
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectar", PO_View.getTimeout());
	}
	
	// Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un listado que
	// contenga varias invitaciones recibidas
	@Test
	public void prueba17() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");
		
		driver.navigate().to(URL);
		// Comprobamos que hay 3
		Assert.assertEquals(3, PO_PrivateView.goToInvitations(driver).size());
		// Comprobamos que son las esperadas
		PO_NavView.checkElement(driver, "text", "Sonia");
		PO_NavView.checkElement(driver, "text", "usuario");
		PO_NavView.checkElement(driver, "text", "admin");
	}
	
	// Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones.
	@Test
	public void prueba18() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");
		
		driver.navigate().to(URL);
		PO_PrivateView.goToInvitations(driver);
		// Aceptamos la invitacion de Sonia
		PO_NavView.checkElement(driver, "id", "accept/Sonia").get(0).click();
		
		// Comprobamos que ya no está
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Sonia", PO_View.getTimeout());
	}
}
