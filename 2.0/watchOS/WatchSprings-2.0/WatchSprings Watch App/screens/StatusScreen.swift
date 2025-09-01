// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import SwiftUI

struct StatusScreen: View
{
    @State var viewModel: StatusViewModel

    var body: some View
    {
        ChildScreen(title: "Server Status", isLoading: viewModel.isLoading)
        {
            VStack(spacing: 8)
            {

                ForEach(viewModel.fortniteComponents) { component in
                    StatusCard(component: component)
                }
            }
        }
        .onAppear
        {
            Task
            {
                await viewModel.refreshStatus()
            }
        }
        .alert(item: Binding(
            get: { viewModel.error.map { ErrorWrapper(message: $0) } },
            set: { _ in viewModel.error = nil }
        )) { errorWrapper in
            Alert(title: Text("Error"),
                  message: Text(errorWrapper.message),
                  dismissButton: .default(Text("OK")))
        }
    }
}

struct StatusCard: View
{
    let component: StatusComponent

    var body: some View
    {
        HStack
        {
            Text(component.name)
                .font(.caption2)
                .frame(maxWidth: .infinity, alignment: .leading)

            StatusDot(status: component.status)
        }
        .padding(8)
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

struct StatusDot: View
{
    let status: String

    var body: some View
    {
        Circle()
            .fill(status == "operational" ? Color.green : Color.red)
            .frame(width: 10, height: 10)
    }
}

private struct ErrorWrapper: Identifiable {
    let message: String
    var id: String { message }
}
