package com.rapid7.container.analyzer.docker.service;

import com.rapid7.container.analyzer.docker.model.image.Image;
import com.rapid7.container.analyzer.docker.model.image.ImageId;
import com.rapid7.container.analyzer.docker.model.image.OperatingSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DockerImageAnalyzerServiceTest {

  @Test
  public void test() throws IOException {
    // Given
    File tarFile = new File("fakealpine.tar");
    ImageId expectedId = new ImageId("sha256:7be494284b1dea6cb2012a5ef99676b4ec22868d9ee235c60e48181542d70fd5");
    OperatingSystem expectedOs = new OperatingSystem("Alpine", "Linux", "Linux", "x86_64", "3.8.0", "Alpine Linux 3.8.0");
    long expectedSize = 119296;
    long expectedLayers = 2;
    long expectedPackages = 66;

    // When
    DockerImageAnalyzerService analyzer = new DockerImageAnalyzerService(null);
    Path tmpdir = Files.createTempDirectory("r7dia");
    Image image = analyzer.analyze(tarFile, tmpdir.toString());

    // Then
    assertEquals(expectedId, image.getId());
    assertEquals(expectedOs, image.getOperatingSystem());
    assertEquals(expectedSize, image.getSize());
    assertEquals(expectedLayers, image.getLayers().size());
    assertEquals(expectedPackages, image.getPackages().size());
  }

}
