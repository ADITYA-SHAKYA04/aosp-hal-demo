// HapticsManagerTest.java
package com.example.haptics;
import org.junit.Test;
import static org.mockito.Mockito.*;
public class HapticsManagerTest {
    @Test
    public void testVibrateAndStop() {
        IHapticsService service = mock(IHapticsService.class);
        HapticsManager manager = new HapticsManager(service);
        manager.vibrate(100, 50);
        manager.stop();
        verify(service).vibrate(100, 50);
        verify(service).stop();
    }
}
