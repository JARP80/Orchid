package com.subgraph.orchid.circuits;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.subgraph.orchid.DirectoryCircuit;
import com.subgraph.orchid.Router;
import com.subgraph.orchid.Stream;
import com.subgraph.orchid.StreamConnectFailedException;
import com.subgraph.orchid.circuits.path.CircuitPathChooser;
import com.subgraph.orchid.circuits.path.PathSelectionFailedException;

public class DirectoryCircuitImpl extends CircuitImpl implements DirectoryCircuit {
	
	protected DirectoryCircuitImpl(CircuitManagerImpl circuitManager) {
		super(circuitManager);
	}
	
	public Stream openDirectoryStream(long timeout) throws InterruptedException, TimeoutException, StreamConnectFailedException {
		final StreamImpl stream = createNewStream();
		try {
			stream.openDirectory(timeout);
			return stream;
		} catch (Exception e) {
			removeStream(stream);
			return processStreamOpenException(e);
		}
	}

	@Override
	protected List<Router> choosePath(CircuitPathChooser pathChooser) throws InterruptedException, PathSelectionFailedException {
		return pathChooser.chooseDirectoryPath();
	}

	@Override
	protected String getCircuitTypeLabel() {
		return "Directory";
	}
}