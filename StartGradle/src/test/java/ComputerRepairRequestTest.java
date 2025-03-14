import model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {
    @Test
    @DisplayName("First test for ComputerRepairRequest")
    public void testComputerRepairRequest() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest(1, "A A", "Address A", "072222", "Asus", "13/10/2020", "Broken display");
        assertEquals(1, computerRepairRequest.getID());
        assertEquals("A A", computerRepairRequest.getOwnerName());
        assertEquals("Address A", computerRepairRequest.getOwnerAddress());
        assertEquals("072222", computerRepairRequest.getPhoneNumber());
        assertEquals("Asus", computerRepairRequest.getModel());
        assertEquals("13/10/2020", computerRepairRequest.getDate());
        assertEquals("Broken display", computerRepairRequest.getProblemDescription());
    }

    @Test
    @DisplayName("Second test for ComputerRepairRequest")
    public void testComputerRepairRequest2() {
        ComputerRepairRequest computerRepairRequest = new ComputerRepairRequest();
        assertEquals("", computerRepairRequest.getOwnerName());
        assertEquals("", computerRepairRequest.getOwnerAddress());
    }

      
}
