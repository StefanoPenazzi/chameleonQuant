/**
 * 
 */
package controller;

/**
 * @author stefanopenazzi
 *
 */

/**
 * Several controllers can run in parallel on several threads 
 * the controller main tasks are:
 * 
 * 1)create an Injector based on the custom binds specificlly for the set of analysis
 * have to be conducted 
 * 
 * 2) run all the analysis specified by the user
 *
 */

public interface ControlerI extends Runnable{

}
